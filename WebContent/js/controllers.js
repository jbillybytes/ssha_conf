var sshaconfControllers = angular.module('sshaconfControllers', [ 'ngGrid','ngAnimate', 'ngSanitize', 'mgcrea.ngStrap' ]);
var baseURI = "http://localhost:8080/ssha_conf/api";//rschweb.ciserrsch.cornell.edu

sshaconfControllers.controller('PeopleSrchCtrl', [ '$scope', '$routeParams', 'People',
		function($scope, $routeParams, People) {

			$scope.searchPeople = function(srchTxt) {
				if (srchTxt.length > 2) {
					$scope.people = People.query({
						srchTxt : srchTxt
					});
				}
			};

			$scope.subtitle = 'User Management';
		} ]);

sshaconfControllers.controller('PeopleDetailCtrl', [ '$scope', '$routeParams',	'Person', 'PersonPublications',
	      function($scope, $routeParams, Person, PersonPublications) {
			var peopleId = $routeParams.id;
	
			$scope.person = Person.get({
				id : peopleId
			});
			/*
			$scope.publicationAuthors = PublicationAuthors.query({
   				pubId : pubID
   			});
   			
			*/
			$scope.personPublications = PersonPublications.query({
				id : peopleId
			});
		} ]);

sshaconfControllers.controller('PublicationListCtrl', [
   		'$scope',
   		'Publications',
   		'$filter',
   		function($scope, Publications, $filter) {
   			$scope.allData = Publications.query();
   			$scope.subtitle = 'Publications';
   			$scope.filterOptions = {
   				filterText : "",
   				useExternalFilter : false
   			};
   			
   			$scope.totalServerItems = 0;	

   			$scope.gridOptions = {
   				data : 'allData',
   				enableColumnResize : true,
   				columnDefs : [ {
   					field : 'title',
   					displayName : 'Title',
   					width: '600',
   					cellTemplate: '<a href="#/publications/{{row.getProperty(\'id\')}}">{{row.getProperty(col.field)}}</a>'
   				}, {
   					field : 'type',
   					displayName : 'Type'
   				}, {
   					field : 'networkName',
   					displayName : 'Network'
   				}, {
   					field : 'StatusName',
   					displayName : 'Status'
   				}],
   				enableRowSelection : false,
   				showFooter : true,
   				showFilter : true,
   				showColumnMenu : true,
   				sortInfo : $scope.sortInfo,
   				useExternalSorting : false,
   				totalServerItems : 'totalServerItems',
   				showGroupPanel: true,
   				filterOptions : $scope.filterOptions
   			};
   		} ]);

sshaconfControllers.controller('PublicationDetailCtrl', [ '$scope', '$routeParams',	'Publication', 'PublicationAuthors',
           function($scope, $routeParams, Publication, PublicationAuthors) {
			var pubID = $routeParams.pubId;
			
   			$scope.publication = Publication.get({
   				pubId : pubID
   			});
   			
   			$scope.publicationAuthors = PublicationAuthors.query({
   				pubId : pubID
   			});
   			
   			
   			//console.log($scope.publicationAuthors)
   		} ]);

sshaconfControllers.controller('PublicationDetailEditCtrl', [ '$scope',
		'$routeParams', 'Publication', 'PublicationAuthors', '$http',
		function($scope, $routeParams, Publication, PublicationAuthors, $http) {
			var pubID = $routeParams.pubId;

			$scope.publicationAuthors = PublicationAuthors.query({
				pubId : pubID
			});
			
			$http.get(baseURI + '/publications/'+pubID).success(function(data_publication) {
				$scope.publication = data_publication;
				
				$http.get(baseURI + '/networks/').success(function(data_networks) {
					$scope.networks = data_networks;
					$scope.network = $scope.publication.network_id;
					
					$scope.pubStatus = $scope.publication.is_submitted == 1 ? "Unsubmit" : "Submit";
					//console.log($scope.publication.is_submitted);
					
					$http.get(baseURI + '/statuses').success(function(data_statuses) {
						$scope.statuses = data_statuses;
						$scope.status = $scope.publication.StatusID;
						
						$http.get(baseURI + '/pub_types').success(function(data_pub_types) {
							$scope.pub_types = data_pub_types;
							$scope.pub_type = $scope.publication.type;
							
							$http.get(baseURI + '/sessions').success(function(data_sessions) {
								$scope.sessions = data_sessions;
								$scope.session = $scope.publication.conference_session_id;
							});
						});
					});
				});
			});
			
		} ]);


sshaconfControllers.controller('SessionListCtrl', [
		'$scope',
		'Sessions',
		'$filter',
		function($scope, Sessions, $filter) {
			$scope.allData = Sessions.query();
			$scope.subtitle = 'Network Sessions';
			$scope.filterOptions = {
				filterText : "",
				useExternalFilter : false
			};
			
			$scope.totalServerItems = 0;	

			$scope.gridOptions = {
				data : 'allData',
				enableColumnResize : true,
				columnDefs : [ {
					field : 'title',
					displayName : 'Title',
					width: '600',
					cellTemplate: '<a href="#/sessions/{{row.getProperty(\'id\')}}">{{row.getProperty(col.field)}}</a>'
				}, {
					field : 'type',
					displayName : 'Session Type'
				}, {
					field : 'networkName',
					displayName : 'Network'
				}, {
					field : 'statusName',
					displayName : 'Status'
				}],
				enableRowSelection : false,
				showFooter : true,
				showFilter : true,
				showColumnMenu : true,
				sortInfo : $scope.sortInfo,
				useExternalSorting : false,
				totalServerItems : 'totalServerItems',
				showGroupPanel: true,
				filterOptions : $scope.filterOptions
			};
		} ]);

sshaconfControllers.controller('SessionDetailCtrl', [ '$scope', '$routeParams', 
	'Session', 'SessionPeople', 'SessionAvailableVolunteers', 'SessionPublications', 
	'NetworkRepresentatives','NetworkOrphanPapers', '$http',
	function($scope, $routeParams, Session, SessionPeople, SessionAvailableVolunteers, 
			SessionPublications, NetworkRepresentatives, NetworkOrphanPapers, $http) {
		var sId = $routeParams.sessionId;

		//I used $http here (which is fine, but a little different than the rest of the code below)
		//b/c I didn't have time to figure out how to do sequential calls with $resource and $q
		$http.get(baseURI + '/sessions/' + sId).success(function(data) {
		    $scope.session = data;
		    var network_id = $scope.session.primary_network_id;
		    
		    $scope.networkRepresentatives = NetworkRepresentatives.query({
				networkId : network_id				
			});
		    
		    $scope.networkOrphanPapers = NetworkOrphanPapers.query({
				networkId : network_id				
			});
		});
					
		$scope.sessionPeople = SessionPeople.query({
			sessionId : sId
		});
		
		$scope.sessionAvailableVolunteers = SessionAvailableVolunteers.query({
			sessionId : sId
		});
		
		$scope.sessionPublications = SessionPublications.query({
			sessionId : sId
		});	    
		
		
} ]);


sshaconfControllers.controller('DropdownDemoCtrl', function($scope, $window) {
	$scope.mySshaMenu = [ {
		text : 'My Info',
		href : '#/mySSHA'
	}, {
		text : 'Create Paper/Poster',
		click : '$alert(\'working ngClick!\')'
	}, {
		text : 'Create Session',
		href : '#separatedLink'
	}, {
		text : 'Volunteer',
		href : '#separatedLink'
	} ];

	$scope.netRepMenu = [ {
		text : 'Network Sessions',
		href : '#/sessions'
	}, {
		text : 'Network Paper/Posters',
		href : '#/publications'
	}];

	$scope.adminMenu = [ {
		text : 'System Settings',
		href : '#anotherAction'
	}, {
		text : 'User Management',
		href : '#/people'
	}, {
		text : 'Reporting',
		href : '#separatedLink'
	}, {
		text : 'Scheduling',
		href : '#separatedLink'
	}, {
		text : 'Communicate',
		href : '#separatedLink'
	} ];

});
