class LogoutController {

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index() {
        request.logout()
        redirect(uri: "/")

        return
    }

}
