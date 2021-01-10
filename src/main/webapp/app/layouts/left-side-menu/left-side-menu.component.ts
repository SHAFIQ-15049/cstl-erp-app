import { SidebarService } from './../../shared/sidebar.service';
import { Component, Input, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-left-side-menu',
  templateUrl: './left-side-menu.component.html',
  styles: [],
})
export class LeftSideMenuComponent implements OnInit {
  employeeName = '';
  username = '';
  entities = false;
  employeeManagement = false;
  payrollManagement = false;

  sidebarWidth = 0;
  sideMarginLeft = 0;

  weekendManagement = false;
  holidayManagement = false;
  leaveManagement = false;
  attendanceManagement = false;

  @Input() leftMenuHidden = false;
  constructor(private accountService: AccountService, private mobileSidebarHideService: SidebarService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(res => {
      this.employeeName = res?.firstName! + ' ' + res?.lastName!;
      this.username = res?.login!;
    });
  }

  mobileSidebarHide(): void {
    this.mobileSidebarHideService.sendClickEvent();
  }

  getSidebarWidth(): number {
    return (this.sidebarWidth = window.innerWidth);
  }
}
