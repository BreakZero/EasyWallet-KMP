//
//  HomeUiState.swift
//  Wallet-iOS
//
//  Created by Jin on 2023/9/18.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

enum HomeUiState {
    case Fetching
    case GuestUiState(String)
    case WalletUiState(DashboardInformation)
}
