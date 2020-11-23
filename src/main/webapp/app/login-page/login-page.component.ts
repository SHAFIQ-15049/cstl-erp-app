import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LoginModalComponent } from 'app/shared/login/login.component';
import { LoginService } from 'app/core/login/login.service';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent extends LoginModalComponent implements AfterViewInit {
  constructor(
    protected loginService: LoginService,
    protected router: Router,
    public activeModal: NgbActiveModal,
    protected fb: FormBuilder
  ) {
    super(loginService, router, activeModal, fb);
  }
}
