import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {MainModule} from "./module/main.module";

const platform = platformBrowserDynamic();
platform.bootstrapModule(MainModule);