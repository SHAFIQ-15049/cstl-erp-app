export class ReportUtil {
  public static writeFileContent(data: any, contentType: string, fileName: string): any {
    const file = new Blob([data], { type: contentType });
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      ReportUtil.saveAsFile(reader.result, fileName);
    };
  }

  public static saveAsFile(url: any, fileName: string): any {
    const a: any = document.createElement('a');
    document.body.appendChild(a);
    a.style = 'display: none';
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
  }
}
