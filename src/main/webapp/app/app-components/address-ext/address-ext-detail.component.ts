import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import { IAddress } from 'app/shared/model/address.model';
import {AddressDetailComponent} from "app/entities/address/address-detail.component";

@Component({
  selector: 'jhi-address-detail',
  templateUrl: './address-ext-detail.component.html',
})
export class AddressExtDetailComponent extends AddressDetailComponent implements OnInit {
  showLoader?: boolean;

  constructor(protected activatedRoute: ActivatedRoute, private route: Router) {
    super(activatedRoute);
  }

  ngOnInit(): void {
    this.showLoader = true;
    this.activatedRoute.data.subscribe(({ address }) => {
      this.address = address;
      const employeeId = this.address?.employee?.id;
      if(!address.id){
        setInterval(()=>{
          this.route.navigate(['../../../address', employeeId,'new'], {relativeTo: this.activatedRoute});
        },2000);
      }else{
        this.showLoader = false;
      }
    });
  }

}
