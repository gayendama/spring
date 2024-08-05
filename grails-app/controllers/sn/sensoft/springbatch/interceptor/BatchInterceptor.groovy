package sn.sensoft.springbatch.interceptor


class BatchInterceptor {

    int order = LOWEST_PRECEDENCE + 10

    boolean before() {
        return true
    }

    boolean after() {
        true
    }

    void afterView() {
        null
    }

    BatchInterceptor() {
        matchAll().excludes(controller: 'healthCheck')
                .excludes(controller: 'logout')
                .excludes(controller: 'backup')
    }

}
