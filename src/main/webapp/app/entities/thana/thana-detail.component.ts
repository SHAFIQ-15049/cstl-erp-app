import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThana } from 'app/shared/model/thana.model';

@Component({
  selector: 'jhi-thana-detail',
  templateUrl: './thana-detail.component.html',
})
export class ThanaDetailComponent implements OnInit {
  thana: IThana | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thana }) => (this.thana = thana));
  }

  previousState(): void {
    window.history.back();
  }
}
