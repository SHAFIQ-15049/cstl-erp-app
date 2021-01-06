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
  weekendManagement = false;
  holidayManagement = false;
  leaveManagement = false;
  attendanceManagement = false;

  @Input() leftMenuHidden = false;
  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(res => {
      this.employeeName = res?.firstName! + ' ' + res?.lastName!;
      this.username = res?.login!;
    });
  }
}
