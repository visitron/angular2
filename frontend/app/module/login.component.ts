import {Component, OnInit} from "@angular/core";
import {DataProvider} from "./service/data.service";
import {NgForm} from "@angular/forms";
import {Headers, Http} from "@angular/http";
import {Router} from "@angular/router";
import {ConfigProvider} from "./service/config.service";
import {Auth} from "./service/auth.service";

@Component({
    templateUrl: 'mockup/login.html'
})
export class LoginComponent implements OnInit {

    private users: User[] = [];
    private user: User = null;
    private lastLoginFailed: boolean = false;

    constructor(private dataProvider: DataProvider, private router: Router, private http: Http,
                private configProvider: ConfigProvider, private auth: Auth) {
        console.log('LoginComponent has been created');
    }

    ngOnInit(): void {
        console.log('Users has been requested');
        this.dataProvider.getUsers((users: User[]) => {
            this.users = users.sort((u1, u2) => {
                if (u1.role != u2.role) {
                    return u1.role == 'ADMIN' ? 1 : -1;
                }
                if (u1.firstName != u2.firstName) {
                    return u1.firstName.localeCompare(u2.firstName);
                }
                return u1.secondName.localeCompare(u2.secondName);
            });
        });
    }

    getUserClass(user: User): string {
        return user.role == 'USER' ? 'label label-success' : 'label label-danger';
    }

    getImageURL(user: User): string {
        return user.photo ? `${this.configProvider.host}/public/images/${user.id}.jpg` : '/mockup/nophoto.jpg'
    }

    selectUser(user: User): void {
        this.lastLoginFailed = false;
        this.user = user;
        this.user.password = null;
    }

    selectedUser(): string {
        if (this.user === null) {
            return 'Select User';
        } else {
            return `${this.user.firstName} ${this.user.secondName}`;
        }
    }

    canLogin(user: User): boolean {
        return user === null || _.isEmpty(user.password);
    }

    login(loginForm: NgForm, user: User) {
        let formData: FormData = new FormData();
        formData.append("username", user.firstName);
        formData.append("password", user.password);
        formData.append("remember-me", user.rememberMe);

        let headers = new Headers;
        this.http.post(`${this.configProvider.host}/login/auth`, formData, {headers: headers, withCredentials: true}).subscribe(
            data => {
                this.auth.user = new User(data.headers.get('Role'));
                localStorage.setItem('homeManagementUser', JSON.stringify(this.auth.user));
                this.router.navigate(this.auth.initialUrl);
            },
            error => {
                this.lastLoginFailed = true;
            }
        );

    }

}

export class User {
    public id: number;
    public firstName: string;
    public secondName: string;
    public role: string;
    public password: string;
    public photo: boolean;
    public state: 'DRAFT' | 'ACTIVE' | 'BLOCKED';
    public rememberMe: boolean;

    constructor(role?: string) {
        if (!_.isEmpty(role)) this.role = role;
    }
}
