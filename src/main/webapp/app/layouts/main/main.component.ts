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
      @media only screen and (max-width: 640px) {
        .main-wrapper span jhi-left-side-menu {
          height: 100%;
          margin-top: 50px;
          position: fixed;
          top: 0;
          left: 0;
          z-index: 10;
          overflow-x: hidden;
          padding-left: 10px;
          padding-top: 20px;
          transition: 0.1s;
        }
      }
    `,
  ],
})
export class MainComponent implements OnInit {
  clickEventsubscription: Subscription;
  isToggle = true;
  isOpen = 'open';
  sideWidth = 0;
  num = 0;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private sidebarService: SidebarService
  ) {
    this.clickEventsubscription = this.sidebarService.getClickEvent().subscribe(() => {
      if (window.innerWidth > 640) this.doLeftSideMenuHide();
      else this.mobileSidebarHide();
    });
  }

  //method for toggling the isToggle state
  doLeftSideMenuHide(): void {
    if (window.innerWidth > 640) {
      this.isToggle = !this.isToggle;
    }
  }

  public get width(): any {
    return window.innerWidth;
  }

  mobileSidebarHide(): void {
    if (window.innerWidth <= 640 && this.num !== 0) {
      this.isToggle = !this.isToggle;
    }

    if (window.innerWidth <= 640) {
      if (this.num === 0) {
        this.isToggle = true;
        this.num++;
      }
      this.sideWidth = 230;
    } else {
      this.sideWidth = 0;
    }
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
