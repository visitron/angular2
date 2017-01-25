import {Component, OnInit} from "@angular/core";
import {DataProvider} from "./service/data.service";

@Component({
    templateUrl: 'mockup/login.html'
})
export class LoginComponent implements OnInit {

    private users: User[] = [];

    constructor(private dataProvider: DataProvider) {
        console.log('LoginComponent has been created');
    }

    ngOnInit(): void {
        console.log('Users has been requested');
        this.dataProvider.getUsers((users: User[]) => {
            this.users = users;
        });
    }

    getUserClass(user: User): string {
        return user.role == 'USER' ? 'label label-success' : 'label label-danger';
    }

    getImageURL(user: User): string {
        return user.photo ? `http://localhost:3002/public/images/${user.id}.jpg` : '/mockup/nophoto.jpg'
    }

}

class User {
    public id: number;
    public firstName: string;
    public secondName: string;
    public role: string;
    public password: string;
    public photo: boolean;
    public state: 'DRAFT' | 'ACTIVE' | 'BLOCKED';
}
