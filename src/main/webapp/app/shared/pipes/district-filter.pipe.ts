import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'districtFilter'
})
export class DistrictFilterPipe implements PipeTransform {

  transform(value: any[], districtId: number): any[] {
    return value.filter((d)=> d?.district.id === districtId);
  }

}
