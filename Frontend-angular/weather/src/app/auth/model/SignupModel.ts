export class SignupModel {
    
    name: string;
    mail: string;
    phone: string;
    password: string;
    role: string[];
 
    constructor(name: string, email: string, phone: string, password: string) {
        this.name = name;
        this.phone = phone;
        this.mail = email;
        this.password = password;
        this.role = ['user'];
    }
}