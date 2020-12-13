import { Pipe, PipeTransform } from '@angular/core';
import {IGrade} from "app/shared/model/grade.model";
import {EmployeeCategory} from "app/shared/model/enumerations/employee-category.model";

@Pipe({
  name: 'gradeFilter'
})
export class GradeFilterPipe implements PipeTransform {

  transform(value: IGrade[], category: EmployeeCategory): IGrade[] {
    return value.filter((g)=> g?.category=== category);
  }

}
