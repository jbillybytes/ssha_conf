var sshaconfServices = angular.module('sshaconfServices', [ 'ngResource' ]);
var baseServURI = "http://localhost:8080/ssha_conf/api";//rschweb.ciserrsch.cornell.edu 

sshaconfServices.factory('Network', ['$resource', function($resource) {
	return $resource(baseServURI + '/networks/:networkId',	{}, {
		query : {
			method : 'GET',
			params : { networkId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('NetworkRepresentatives', ['$resource', function($resource) {
	return $resource(baseServURI + '/networks/:networkId/representatives',	{}, {
		query : {
			method : 'GET',
			params : { networkId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('NetworkOrphanPapers', ['$resource', function($resource) {
	return $resource(baseServURI + '/networks/:networkId/orphan_papers',	{}, {
		query : {
			method : 'GET',
			params : { networkId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Networks', [ '$resource', function($resource) {
	return $resource(baseServURI + '/networks', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('People', [ '$resource', function($resource) {
	return $resource(baseServURI + '/:verb', {verb:'people', srchTxt:'' }, {
		query : {
			method : 'GET',
			params : { srchTxt : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Person', [ '$resource', function($resource) {
	return $resource(baseServURI + '/people/:id', {}, {
		query : {
			method : 'GET',
			params : { id : '' },
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('PersonPublications', [ '$resource', function($resource) {
	return $resource(baseServURI + '/people/:id/publications', {}, {
		query : {
			method : 'GET',
			params : { id : '' },
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Publication', ['$resource', function($resource) {
	return $resource(baseServURI + '/publications/:pubId',	{}, {
		query : {
			method : 'GET',
			params : { pubId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('PublicationAuthors', ['$resource', function($resource) {
	return $resource(baseServURI + '/publications/:pubId/authors',	{}, {
		query : {
			method : 'GET',
			params : { pubId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Publications', [ '$resource', function($resource) {
	return $resource(baseServURI + '/publications', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Session', ['$resource', function($resource) {
	return $resource(baseServURI + '/sessions/:sessionId',	{}, {
		query : {
			method : 'GET',
			params : { sessionId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('SessionAvailableVolunteers', ['$resource', function($resource) {
	return $resource(baseServURI + '/sessions/:sessionId/available_volunteers', {}, {
		query : {
			method : 'GET',
			params : { sessionId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('SessionPeople', ['$resource', function($resource) {
	return $resource(baseServURI + '/sessions/:sessionId/people', {}, {
		query : {
			method : 'GET',
			params : { sessionId : ''},
			isArray : true
		}
	});
} ]);


sshaconfServices.factory('SessionPublications', ['$resource', function($resource) {
	return $resource(baseServURI + '/sessions/:sessionId/publications', {}, {
		query : {
			method : 'GET',
			params : { sessionId : ''},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Sessions', [ '$resource', function($resource) {
	return $resource(baseServURI + '/sessions', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : true
		}
	});
} ]);

sshaconfServices.factory('Statuses', [ '$resource', function($resource) {
	return $resource(baseServURI + '/statuses', {}, {
		query : {
			method : 'GET',
			params : {},
			isArray : true
		}
	});
} ]);

