import {Validator, AbstractControl, NG_VALIDATORS} from "@angular/forms";
import {Directive, Attribute} from "@angular/core";

@Directive({
    selector: '[confirmPassword]',
    providers: [{provide: NG_VALIDATORS, useExisting: ConfirmPasswordValidator, multi: true}]
})
//todo rename to EqualValidator
export class ConfirmPasswordValidator implements Validator {

    constructor(@Attribute('confirmPassword') private val: any) {
        debugger;
    }

    validate(c: AbstractControl): {[p: string]: any} {
        // return {'confirmPassword': true};

        return null;
    }
}

