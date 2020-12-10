import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'divisionFilter'
})
export class DivisionFilterPipe implements PipeTransform {

  transform(value: any[], divisionId: number): any[] {
    return value.filter((v)=> v?.division.id === divisionId);
  }

}
