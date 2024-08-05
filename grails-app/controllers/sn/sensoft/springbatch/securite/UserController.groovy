package sn.sensoft.springbatch.securite

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 1000)
        respond userList: User.list(), model: [userCount: User.count()]
    }


    def show(User userInstance) {
        userInstance= User.get(params?.id)
        [userInstance: userInstance]
    }

    def showProfile(User userInstance) {
        respond userInstance: userInstance
    }

    def create() {
        respond userInstance: new User(params)
    }



}
