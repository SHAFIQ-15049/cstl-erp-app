import { Injectable } from '@angular/core';
import { SessionStorageService } from 'ngx-webstorage';

@Injectable({ providedIn: 'root' })
export class StateStorageService {
  private previousUrlKey = 'previousUrl';
  private previousUrlKeyCustom = 'previousUrlCustom';

  constructor(private $sessionStorage: SessionStorageService) {}

  storeUrl(url: string): void {
    this.$sessionStorage.store(this.previousUrlKey, url);
  }

  getUrl(): string | null | undefined {
    return this.$sessionStorage.retrieve(this.previousUrlKey);
  }

  clearUrl(): void {
    this.$sessionStorage.clear(this.previousUrlKey);
  }
  /*
  * Custom URL store used for avoiding collision of account related expiration.
  * auth-expired-interceptor.ts is using the storeUrl() function. So
  * the same method will be called if both time auth is expired and we need to go back
  * to the employee list. That's why customization (methods start with 'custom' prefix) is used.
  * */

  storeCustomUrl(url: string): void{
    this.$sessionStorage.store(this.previousUrlKeyCustom, url);
  }

  getCustomUrl(): string | null | undefined{
    return this.$sessionStorage.retrieve(this.previousUrlKeyCustom);
  }

  clearCustomUrl(): void{
    this.$sessionStorage.clear(this.previousUrlKeyCustom);
  }
}
