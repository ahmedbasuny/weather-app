export class LoginModel { 

    mail: string;
    password: string;
 
    constructor(mail: string, password: string) {
        this.mail = mail;
        this.password = password;
    }
}