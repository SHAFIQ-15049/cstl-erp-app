import { Moment } from 'moment';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';

export interface ICustomer {
  id?: number;
  name?: string;
  fatherOrHusband?: string;
  address?: string;
  sex?: GenderType;
  phoneNo?: string;
  nationality?: string;
  dateOfBirth?: Moment;
  guardiansName?: string;
  chassisNo?: string;
  engineNo?: string;
  yearsOfMfg?: number;
  preRegnNo?: string;
  poOrBank?: string;
  voterIdNo?: string;
  voterIdAttachmentContentType?: string;
  voterIdAttachment?: any;
  passportAttachmentContentType?: string;
  passportAttachment?: any;
  birthCertificateAttachmentContentType?: string;
  birthCertificateAttachment?: any;
  gassOrWaterOrElectricityBillContentType?: string;
  gassOrWaterOrElectricityBill?: any;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public fatherOrHusband?: string,
    public address?: string,
    public sex?: GenderType,
    public phoneNo?: string,
    public nationality?: string,
    public dateOfBirth?: Moment,
    public guardiansName?: string,
    public chassisNo?: string,
    public engineNo?: string,
    public yearsOfMfg?: number,
    public preRegnNo?: string,
    public poOrBank?: string,
    public voterIdNo?: string,
    public voterIdAttachmentContentType?: string,
    public voterIdAttachment?: any,
    public passportAttachmentContentType?: string,
    public passportAttachment?: any,
    public birthCertificateAttachmentContentType?: string,
    public birthCertificateAttachment?: any,
    public gassOrWaterOrElectricityBillContentType?: string,
    public gassOrWaterOrElectricityBill?: any
  ) {}
}
