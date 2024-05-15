//
//  HomeUiState.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/18.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import platform_shared

enum WalletUiState {
    case Fetching
    case GuestUiState(String)
    case UserUiState(AllAssetDashboardInformation)
}
