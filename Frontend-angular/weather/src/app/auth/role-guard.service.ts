import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  private roles: string[];
  private authority: string;

  constructor(private router: Router, private token: TokenStorageService) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    console.log("canActivate function has been called.")
    this.roles = this.token.getAuthorities();
    this.roles.every(role => {
      if (role === 'ROLE_ADMIN') {
        this.authority = 'admin';
        return true;
      }
    });
    if (this.authority === 'admin') {
      return true;
    }
    this.router.navigate(['/notauth']);
    return false;
  }
}
