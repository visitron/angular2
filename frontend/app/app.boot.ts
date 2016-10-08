import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {MaintenanceModule} from "./module/app.module";

const platform = platformBrowserDynamic();
platform.bootstrapModule(MaintenanceModule);