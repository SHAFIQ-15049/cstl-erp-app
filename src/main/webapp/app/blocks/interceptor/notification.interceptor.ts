import { JhiAlertService } from 'ng-jhipster';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable()
export class NotificationInterceptor implements HttpInterceptor {
  constructor(private alertService: JhiAlertService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          let alert: string | null = null;

          event.headers.keys().forEach(entry => {
            if (entry.toLowerCase().endsWith('app-alert')) {
              const alertMsg = event.headers.get(entry);
              if (alertMsg!.includes('created')) alert = 'Creation success';
              else if (alertMsg!.includes('updated')) alert = 'Updated successfully';
              else if (alertMsg!.includes('deleted')) alert = 'Deleted successfully';
            }
          });

          if (alert) {
            this.alertService.success(alert);
          }
        }
      })
    );
  }
}
