import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  private subject = new Subject<any>();
  sendClickEvent(): void {
    this.subject.next();
  }
  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }
}
