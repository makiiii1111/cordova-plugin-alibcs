#import <Cordova/CDV.h>
#import <AlibcTradeSDK/AlibcTradeSDK.h>
#import <AlibcTradeSDK/AlibcTradePageFactory.h>
#import <AlibabaAuthExt/AlibabaAuthExt.h>
#import <AlibcTradeBiz/AlibcTradeBiz.h>


@interface CDVAlibcS:CDVPlugin

@property (nonatomic, strong) NSString *currentCallbackId;
@property (nonatomic, strong) NSString *appkey;
@property (nonatomic, strong) NSString *pid;
@property (nonatomic, strong) NSString *adzoneid;

- (void)page:(CDVInvokedUrlCommand *)command;

@end