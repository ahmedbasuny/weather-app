import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { PredefinedNotesComponent } from './predefined-notes/predefined-notes.component';

const routes: Routes = [
    { path: 'home',         component: HomeComponent            },
    { path: 'admin',        component: AdminComponent           }, 
    { path: 'auth/login',   component: LoginComponent           },
    { path: 'signup',       component: RegisterComponent        },
    { path: 'notauth',      component: NotAuthorizedComponent   },
    { path: 'prenote',      component: PredefinedNotesComponent },
    
    {path: '',  redirectTo: 'home', pathMatch: 'full'       }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
