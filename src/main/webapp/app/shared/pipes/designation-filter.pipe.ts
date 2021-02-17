import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'designationFilter',
})
export class DesignationFilterPipe implements PipeTransform {
  transform(value: any[], departmentId: number): any[] {
    return value.filter(d => d?.department.id === departmentId);
  }
}
