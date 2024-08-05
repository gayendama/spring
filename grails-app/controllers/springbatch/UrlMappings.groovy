package springbatch

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'batch', action: 'list')
        "500"(view: '/error')
        "404"(view: '/notFound')
        "403"(view:'/forbidden')

        "/api/$version/program-status"(controller: "programmeBatchApi", parseRequest: true) {
            action = [POST: "programmeStatus"]
        }

    }
}
