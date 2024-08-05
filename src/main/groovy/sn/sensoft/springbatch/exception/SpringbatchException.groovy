package sn.sensoft.springbatch.exception

import groovy.json.JsonOutput

/**
 * Created by abadiane on 25/12/2016.
 */
class SpringbatchException extends RuntimeException {

    private static final long serialVersionUID = 8302460224741666076L

    String codeMsg
    String msgToLog
    Object obj1
    Object obj2
    Object errors

    SpringbatchException() {
        super()
    }

    SpringbatchException(String message, Object errors) {
        super(message)
        this.errors = errors
    }

    SpringbatchException(String message) {
        super(message)
    }

    SpringbatchException(String message, String codeMsg) {
        super(message)
        this.codeMsg = codeMsg
    }

    SpringbatchException(Throwable e) {
        super(e)
    }

    SpringbatchException(String message, String codeMsg, Object errors) {
        super(message)
        this.codeMsg = codeMsg
        this.errors = errors
    }

    SpringbatchException(String message, String codeMsg, String msgToLog, Object errors) {
        super(message)
        this.codeMsg = codeMsg
        this.msgToLog = msgToLog
        this.errors = errors
    }

    SpringbatchException(String message, String codeMsg, Object obj, Object errors) {
        super(message)
        this.codeMsg = codeMsg
        this.obj1 = obj
        this.errors = errors
    }

    SpringbatchException(String message, String codeMsg, String msgToLog, Object obj1, Object errors) {
        super(message)
        this.codeMsg = codeMsg
        this.msgToLog = msgToLog
        this.obj1 = obj1
        this.errors = errors
    }

    SpringbatchException(String message, String codeMsg, Object obj1, Object obj2, Object errors) {
        super(message)
        this.codeMsg = codeMsg
        this.msgToLog = msgToLog
        this.obj1 = obj1
        this.obj2 = obj2
        this.errors = errors
    }

    String getMsgToLog() {
        if (msgToLog) {
            this.msgToLog
        } else {
            this.getMessage()
        }
    }

    String toString() {
        JsonOutput.toJson([status: "error", code: codeMsg, message: message, obj1: obj1, obj2: obj2, msgToLog: msgToLog])
    }
}
