import {Validator, AbstractControl, NG_VALIDATORS} from "@angular/forms";
import {Directive, Attribute} from "@angular/core";
import * as _ from "underscore";

@Directive({
    selector: '[validateEqual],[validateEqualPrimary]',
    providers: [{provide: NG_VALIDATORS, useExisting: EqualValidator, multi: true}]
})
export class EqualValidator implements Validator {

    private validateEqualPrimary: boolean = false;

    constructor(@Attribute('validateEqual') private validateEqual: string,
                @Attribute('validateEqualPrimary') validateEqualPrimary: any) {
        this.validateEqualPrimary = validateEqualPrimary !== null;
    }

    validate(c: AbstractControl): {[p: string]: any} {
        let value1 = c.value;
        let control2 = c.root.get(this.validateEqual);
        let value2 = _.isEmpty(control2) ? null : control2.value;

        if (_.isEmpty(value1) || _.isEmpty(value2)) return {'validateEqual': false};

        if (this.validateEqualPrimary && value1 != value2) {
            return {'validateEqual': false};
        } else if (value1 == value2) {
            if (!_.isEmpty(control2.errors)) {
                delete control2.errors['validateEqual'];
                control2.setErrors(null);
            }
            return null;
        } else if (value1 != value2) {
            return {'validateEqual': false};
        }

    }
}

