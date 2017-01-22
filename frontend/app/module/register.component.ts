import {Component, OnInit} from "@angular/core";
import {DataProvider} from "./service/data.service";

@Component({
    templateUrl: 'mockup/register.html'
})
export class RegisterComponent implements OnInit {
    private userType: string = null;
    private userClass: string = 'null';
    private imageURL: string = null;

    private model: User = new User;

    constructor(private dataProvider: DataProvider) {}

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
            console.log('File uploading has been hooked: ' + ev.target);
            this.imageURL = (<any> ev.target).result;
        };

        reader.readAsDataURL((<any> event.target).files[0]);
    }

    public onSubmit(): void {
        console.log(this.model);
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