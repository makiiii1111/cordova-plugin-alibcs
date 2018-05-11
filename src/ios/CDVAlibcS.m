#import "CDVAlibcS.h"

@implementation CDVAlibcS

- (void)pluginInitialize {
    NSString* appkey = [[self.commandDelegate settings] objectForKey:@"appkey"];
    NSString* pid = [[self.commandDelegate settings] objectForKey:@"pid"];
    if (appkey){
        self.appkey = appkey;
    }
    if (pid) {
        self.pid = pid;
        NSArray* pidArr = [pid componentsSeparatedByString:@"_"];
        self.adzoneid = pidArr.lastObject;
    }
    [self application];
}

- (void)page:(CDVInvokedUrlCommand *)command{
    NSString *url = [command.arguments objectAtIndex:0];
    NSDictionary *params = [command.arguments objectAtIndex:1];
    NSString *openType = [params objectForKey:@"openType"];
    AlibcTradeShowParams *showParams = [self getShowParams:openType];
    AlibcTradeTaokeParams *taokeParams = [[AlibcTradeTaokeParams alloc] init];
    taokeParams.pid = self.pid; //mm_XXXXX为你自己申请的阿里妈妈淘客pid
    taokeParams.adzoneId = self.adzoneid;
    id<AlibcTradePage> page = [AlibcTradePageFactory page:url];
    [[AlibcTradeSDK sharedInstance].tradeService show:self.viewController page:page showParams:showParams taoKeParams:taokeParams trackParam:nil tradeProcessSuccessCallback:^(AlibcTradeResult * _Nullable result) {
        NSLog(@"result:%@",result);
    } tradeProcessFailedCallback:^(NSError * _Nullable error) {
        NSLog(@"error:%@",error);
    }];
}

-(AlibcTradeShowParams*)getShowParams:(NSString *)openType{
    AlibcTradeShowParams *showParams = [[AlibcTradeShowParams alloc] init];
    if([openType isEqualToString: @"taobao"]){
        showParams.openType = AlibcOpenTypeNative;
        showParams.linkKey = @"taobao_scheme";
    }else if([openType isEqualToString: @"h5"]){
        showParams.openType = AlibcOpenTypeH5;
    }else if ([openType isEqualToString: @"tianmao"]){
        showParams.openType = AlibcOpenTypeNative;
        showParams.linkKey = @"tmall_scheme";
    } else {
        showParams.openType = AlibcOpenTypeAuto;
    }
    showParams.backUrl=@"tbopen24826612";
    return showParams;
}
- (BOOL)application{
    // 百川平台基础SDK初始化，加载并初始化各个业务能力插件
    [[AlibcTradeSDK sharedInstance] asyncInitWithSuccess:^{
        
    } failure:^(NSError *error) {
        NSLog(@"Init failed: %@", error.description);
    }];
    
    // 开发阶段打开日志开关，方便排查错误信息
    //默认调试模式打开日志,release关闭,可以不调用下面的函数
    [[AlibcTradeSDK sharedInstance] setDebugLogOpen:YES];
    
    // 配置全局的淘客参数
    //如果没有阿里妈妈的淘客账号,setTaokeParams函数需要调用
    
    
    //设置全局的app标识，在电商模块里等同于isv_code
    //没有申请过isv_code的接入方,默认不需要调用该函数
    [[AlibcTradeSDK sharedInstance] setISVCode:@"quzhouxing"];
    
    // 设置全局配置，是否强制使用h5
    [[AlibcTradeSDK sharedInstance] setIsForceH5:NO];
    
    return YES;
}
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation{
    // 如果百川处理过会返回YES
    if (![[AlibcTradeSDK sharedInstance] application:application openURL:url sourceApplication:sourceApplication annotation:annotation]) {
        // 处理其他app跳转到自己的app
    }
    return YES;
}


//IOS9.0 系统新的处理openURL 的API
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url options:(NSDictionary<NSString *,id> *)options {
    
    
    __unused BOOL isHandledByALBBSDK=[[AlibcTradeSDK sharedInstance] application:application openURL:url options:options];//处理其他app跳转到自己的app，如果百川处理过会返回YES
    
    return YES;
    
    
}
@end
