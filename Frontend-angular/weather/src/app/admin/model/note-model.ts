export class NoteModel {

    constructor(private message: string) { }

    getMessage() {
        return this.message;
    }

    setMessage(message) {
        this.message = message;
    }
}