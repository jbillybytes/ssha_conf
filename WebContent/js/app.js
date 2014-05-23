var sshaconfApp = angular.module('sshaconfApp', [ 'ngRoute',
		'sshaconfControllers', 'sshaconfServices']);

sshaconfApp.config([ '$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	//$locationProvider.html5Mode(true);
	//$locationProvider.hashPrefix = '!';
	 
	$routeProvider.
	when('/sessions', {
		templateUrl : 'partials/NetworkRepTools/sessions-list.html',
		controller : 'SessionListCtrl'
	}).
	when('/sessions/:sessionId', {
		templateUrl : 'partials/NetworkRepTools/session-detail.html',
		controller : 'SessionDetailCtrl'
	}).
	when('/people', {
		templateUrl : 'partials/Administrative/user-management.html',
		controller : 'PeopleSrchCtrl'
	}).
	when('/people/:id', {
		templateUrl : 'partials/Administrative/user-detail.html',
		controller : 'PeopleDetailCtrl'
	}).
	when('/publications', {
		templateUrl : 'partials/NetworkRepTools/pub-list.html',
		controller : 'PublicationListCtrl'
	}).
	when('/publications/:pubId', {
		templateUrl : 'partials/NetworkRepTools/pub-detail.html',
		controller : 'PublicationDetailCtrl'
	}).
	when('/publications/:pubId/edit', {
		templateUrl : 'partials/NetworkRepTools/pub-detail-edit.html',
		controller : 'PublicationDetailEditCtrl'
	}).
	otherwise({
		redirectTo : '/mySSHA'
	});
	
	
} ]);

