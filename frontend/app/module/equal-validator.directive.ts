import {Validator, AbstractControl, NG_VALIDATORS} from "@angular/forms";
import {Directive, Attribute} from "@angular/core";

@Directive({
    selector: '[validateEqual]',
    providers: [{provide: NG_VALIDATORS, useExisting: EqualValidator, multi: true}]
})
export class EqualValidator implements Validator {

    constructor(@Attribute('validateEqual') private validateEqual: any) {}

    validate(c: AbstractControl): {[p: string]: any} {
        let value1 = c.value;
        let value2 = c.root.get(this.validateEqual);
        value2 = _.isEmpty(value2) ? null : value2.value;

        if (value1 == value2) {
            return null
        } else {
            return {'validateEqual': false};
        }
    }
}

