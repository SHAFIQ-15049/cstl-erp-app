import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PersonalInfoService } from 'app/entities/personal-info/personal-info.service';
import { IPersonalInfo, PersonalInfo } from 'app/shared/model/personal-info.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { ReligionType } from 'app/shared/model/enumerations/religion-type.model';
import { BloodGroupType } from 'app/shared/model/enumerations/blood-group-type.model';

describe('Service Tests', () => {
  describe('PersonalInfo Service', () => {
    let injector: TestBed;
    let service: PersonalInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonalInfo;
    let expectedResult: IPersonalInfo | IPersonalInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PersonalInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PersonalInfo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        MaritalStatus.SINGLE,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        0,
        GenderType.MALE,
        ReligionType.ISLAM,
        BloodGroupType.A_POSITIVE,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.create(new PersonalInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            banglaName: 'BBBBBB',
            photo: 'BBBBBB',
            photoId: 'BBBBBB',
            fatherName: 'BBBBBB',
            fatherNameBangla: 'BBBBBB',
            motherName: 'BBBBBB',
            motherNameBangla: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            spouseName: 'BBBBBB',
            spouseNameBangla: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            nationalId: 'BBBBBB',
            nationalIdAttachment: 'BBBBBB',
            nationalIdAttachmentId: 'BBBBBB',
            birthRegistration: 'BBBBBB',
            birthRegistrationAttachment: 'BBBBBB',
            birthRegistrationAttachmentId: 'BBBBBB',
            height: 1,
            gender: 'BBBBBB',
            religion: 'BBBBBB',
            bloodGroup: 'BBBBBB',
            emergencyContact: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            banglaName: 'BBBBBB',
            photo: 'BBBBBB',
            photoId: 'BBBBBB',
            fatherName: 'BBBBBB',
            fatherNameBangla: 'BBBBBB',
            motherName: 'BBBBBB',
            motherNameBangla: 'BBBBBB',
            maritalStatus: 'BBBBBB',
            spouseName: 'BBBBBB',
            spouseNameBangla: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            nationalId: 'BBBBBB',
            nationalIdAttachment: 'BBBBBB',
            nationalIdAttachmentId: 'BBBBBB',
            birthRegistration: 'BBBBBB',
            birthRegistrationAttachment: 'BBBBBB',
            birthRegistrationAttachmentId: 'BBBBBB',
            height: 1,
            gender: 'BBBBBB',
            religion: 'BBBBBB',
            bloodGroup: 'BBBBBB',
            emergencyContact: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PersonalInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
