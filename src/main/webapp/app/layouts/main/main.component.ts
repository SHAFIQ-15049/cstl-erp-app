import { SidebarService } from './../../shared/sidebar.service';
import { Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  styles: [
    `
      @media only screen and (max-width: 600px) {
        .main-wrapper {
          display: inline block;
        }
        div.main-wrapper span jhi-left-side-menu {
          width: 200px;
        }
        .card {
          width: 300px;
        }
      }
    `,
  ],
})
export class MainComponent implements OnInit {
  clickEventsubscription: Subscription;
  isToggle = true;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private sidebarService: SidebarService
  ) {
    this.clickEventsubscription = this.sidebarService.getClickEvent().subscribe(() => {
      this.doLeftSideMenuHide();
    });
  }

  //method for toggling the isToggle state
  doLeftSideMenuHide(): void {
    this.isToggle = !this.isToggle;
  }

  public get width(): any {
    return window.innerWidth;
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'CodeNodeERP';
    }
    this.titleService.setTitle(pageTitle);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
