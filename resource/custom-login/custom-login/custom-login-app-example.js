/*global angular, _ */
; (function() {

    "use strict";

    angular.module("tssCustomLoginApp",
        [
            "app.api"
        ])
        .controller("tssCustomLoginAppController", function(
            $scope,
            apiService,
            apiHttp) {

            $scope.user = {};

            // Expose login function to view.
            $scope.login = login;
            $scope.redirectToWebAuthProvider = apiService.redirectToWebAuthProvider;

            // Get some initial information from the server.
            getServerManifest();

			var p = getQueryParams();

			if (p.username && p.password && p.libraryname)
			{
                apiService.login(p.username, p.password, false, false, null, function() {
					
					location = "/spotfire/wp/OpenAnalysis?file="+p.libraryname+"&configurationBlock=SessionID%3D%22" + p.sessionid + "%22%3B";
				}, null);
			}

            function getQueryParams() {
                var params = [], hash;
                var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&'), hashIndex, numberOfHashes = 0;

                for (var i = 0; i < hashes.length; i++) {
                    hash = hashes[i].split('=');
                    params.push(hash[0]);
                    params[hash[0]] = "";
                    numberOfHashes = hash.length;

                    for (hashIndex = 1; hashIndex < numberOfHashes; hashIndex++) {
                        if (hashIndex > 1) {
                            params[hash[0]] += "=";
                        }

                        params[hash[0]] += hash[hashIndex];
                    }
                }

                return params;
            }

            /**
             * Perform login attempt with.
             */
            function login() {
                // Get username and password from input fields.
                var username = $scope.user.name;
                var password = $scope.user.password;

				// Optional payload that can be passed to the server. Key prefix must start
                // with sf_custom_login_, otherwise they will not be sent in the request.
                var optionalPayload = {
                    sf_custom_login_extra_string: "My own custom login value",
                    sf_custom_login_extra_boolean: true,
                    invalid_key: "keys need to start with sf_custom_login_"
                };

                if (_.isEmpty(username) || _.isEmpty(password)) {
                    // If no username or password entered, display error.
                    $scope.errorInformation = "Wrong username or password";
                    return;
                }

                // Optional callback which is executed when the login is successful.
                function successCallback(response) {
                    apiService.redirectToTargetUrl();
                    return;
                }

                // Optional callback which is executed if login fails.
                function errorCallback(errResponse) {
                    // If the first retry failes, print error and stop.
                    function retryFailedCallback(response) {
                        $scope.errorInformation = "Login retry failed.";
                        return;
                    }

                    if (errResponse.status === 401) {
                        $scope.errorInformation = "Wrong username or password.";
                    } else if (errResponse.status === 403) {

                        // Retry CSRF if there's a mismatch between CSRF token in request and response.
                        return apiService.retryCsrf(errResponse, retryFailedCallback, successCallback);
                    } else if (errResponse.status === 0) {
                        // Getting a status of 0 when server could not be contacted.
                        $scope.errorInformation = "Server could not be contacted";
                    } else {
                        // If anything else then unauthorizd, display error message.
                        $scope.errorInformation = "Internal Server Error";
                    }
                }

                // Do the actual login attempt
                apiService.login(username, password, $scope.serverInfo.allowSaveInformation, false, errorCallback, successCallback, optionalPayload);
            }

            /**
             * Requests information from server.
             */
            function getServerManifest() {
                apiHttp.manifest(function(data) {
                    $scope.serverInfo = data;

                    // Do not show login page if user is already logged in.
                    if (!data.showLoginPage) {
                        apiService.redirectToTargetUrl();
                    }
                });
            }
        });
} ());
