Pod::Spec.new do |spec|
    spec.name                     = 'database'
    spec.version                  = '1.0'
    spec.homepage                 = 'dejinlu.com'
    spec.source                   = { :http=> ''}
    spec.authors                  = 'Dougie'
    spec.license                  = ''
    spec.summary                  = 'Wallet cocoapod submodule'
    spec.vendored_frameworks      = 'build/cocoapods/framework/database.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '16.0'
    spec.dependency 'SQLCipher', '~> 4.0'
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':shared:database',
        'PRODUCT_MODULE_NAME' => 'database',
    }
                
    spec.script_phases = [
        {
            :name => 'Build database',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end