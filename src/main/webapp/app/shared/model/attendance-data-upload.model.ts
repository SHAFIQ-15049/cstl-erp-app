export interface IAttendanceDataUpload {
  id?: number;
  fileUploadContentType?: string;
  fileUpload?: any;
}

export class AttendanceDataUpload implements IAttendanceDataUpload {
  constructor(public id?: number, public fileUploadContentType?: string, public fileUpload?: any) {}
}
