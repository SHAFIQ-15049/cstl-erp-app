import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { ReligionType } from 'app/shared/model/enumerations/religion-type.model';
import { BloodGroupType } from 'app/shared/model/enumerations/blood-group-type.model';

export interface IPersonalInfo {
  id?: number;
  name?: string;
  banglaName?: string;
  photoContentType?: string;
  photo?: any;
  photoId?: string;
  fatherName?: string;
  fatherNameBangla?: string;
  motherName?: string;
  motherNameBangla?: string;
  maritalStatus?: MaritalStatus;
  spouseName?: string;
  spouseNameBangla?: string;
  dateOfBirth?: Moment;
  nationalId?: string;
  nationalIdAttachmentContentType?: string;
  nationalIdAttachment?: any;
  nationalIdAttachmentId?: string;
  birthRegistration?: string;
  birthRegistrationAttachmentContentType?: string;
  birthRegistrationAttachment?: any;
  birthRegistrationAttachmentId?: string;
  height?: number;
  gender?: GenderType;
  religion?: ReligionType;
  bloodGroup?: BloodGroupType;
  emergencyContact?: string;
  employee?: IEmployee;
}

export class PersonalInfo implements IPersonalInfo {
  constructor(
    public id?: number,
    public name?: string,
    public banglaName?: string,
    public photoContentType?: string,
    public photo?: any,
    public photoId?: string,
    public fatherName?: string,
    public fatherNameBangla?: string,
    public motherName?: string,
    public motherNameBangla?: string,
    public maritalStatus?: MaritalStatus,
    public spouseName?: string,
    public spouseNameBangla?: string,
    public dateOfBirth?: Moment,
    public nationalId?: string,
    public nationalIdAttachmentContentType?: string,
    public nationalIdAttachment?: any,
    public nationalIdAttachmentId?: string,
    public birthRegistration?: string,
    public birthRegistrationAttachmentContentType?: string,
    public birthRegistrationAttachment?: any,
    public birthRegistrationAttachmentId?: string,
    public height?: number,
    public gender?: GenderType,
    public religion?: ReligionType,
    public bloodGroup?: BloodGroupType,
    public emergencyContact?: string,
    public employee?: IEmployee
  ) {}
}
