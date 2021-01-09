import { AfterViewInit, Component, OnInit } from '@angular/core';
import { EmployeeExtService } from 'app/app-components/employee-ext/employee-ext.service';
import { ActivatedRoute, Data, ParamMap } from '@angular/router';
import { combineLatest } from 'rxjs';

@Component({
  selector: 'jhi-id-card',
  templateUrl: './id-card.component.html',
  styleUrls: ['./id-card.component.scss'],
})
export class IdCardComponent implements OnInit {
  employeeId?: number;
  pdfData?: any;

  constructor(private employeeExtService: EmployeeExtService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      this.employeeId = +params.get('employeeId')!;
      this.getIdCard();
    }).subscribe();
  }

  public getIdCard(): void {
    this.employeeExtService.downloadIdCard(this.employeeId!).subscribe(data => {
      const file = new Blob([data], { type: 'application/pdf' });
      this.pdfData = URL.createObjectURL(file);
      const link = document.createElement('a');
      link.href = this.pdfData;
      link.download = 'id-card.pdf';
      link.click();
    });
  }
}
