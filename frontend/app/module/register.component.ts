import {Component, OnInit} from "@angular/core";
import {DataProvider} from "./service/data.service";
import {NgForm} from "@angular/forms";
import {Http, Headers} from "@angular/http";
import {Router} from "@angular/router";

@Component({
    templateUrl: 'mockup/register.html'
})
export class RegisterComponent implements OnInit {
    private userType: string = null;
    private userClass: string = 'null';
    private imageURL: string = null;
    private file: File = null;

    private model: User = new User;

    constructor(private dataProvider: DataProvider, private http: Http, private router: Router) {}

    ngOnInit(): void {
        this.dataProvider.hasAdmin(hasAdmin => {
            this.userType = hasAdmin ? 'User' : 'Admin';
            this.userClass = hasAdmin ? 'label label-success' : 'label label-danger';
        });
    }

    public selectFile(): void {
        $('#image-file-id').trigger('click');
    }

    public previewImage(event: Event): void {
        let reader: FileReader = new FileReader;
        reader.onload = (ev: Event) => {
            this.imageURL = (<any> ev.target).result;
        };

        this.file = (<any> event.target).files[0];
        reader.readAsDataURL((<any> event.target).files[0]);
    }

    public onSubmit(form: NgForm, model: User): void {
        console.log(form.valid);

        // if (true) throw new Error;

        let formData: FormData = new FormData();
        formData.append("firstName", model.firstName);
        formData.append("secondName", model.secondName);
        formData.append("email", model.email);
        formData.append("password", model.password);
        formData.append("image", this.file);


        let headers = new Headers;
        // headers.append('Content-Type', 'boundary=---myOwnBoundary---');
        headers.append('Access-Control-Allow-Origin', 'http://localhost:3000');
        headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        headers.append("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        headers.append("Access-Control-Allow-Credentials", "true");
        this.http.post('http://localhost:3002/register/request', formData, {headers: headers}).subscribe(
            (data) => {
                // debugger;
                console.log(data);
                $('#message-registration-id').modal('hide');
                this.router.navigate(['/login']);
            },
            (error) => console.log(error)
        );


        // (<any>form).submit();
        // throw new Error;
    }

    public checkPasswords(form: any, passwordConfirmed: string) {
        if (this.model.password != passwordConfirmed) {
            form.valid = false;
        }
    }
}

class User {
    public firstName: string;
    public secondName: string;
    public email: string;
    public password: string;
    public passwordConfirmed: string;
}