import 'flutter_warehouse_pda_scanner_platform_interface.dart';

class FlutterWarehousePdaScanner {
  Future<String?> getPlatformVersion() =>
      FlutterWarehousePdaScannerPlatform.instance.getPlatformVersion();
}
