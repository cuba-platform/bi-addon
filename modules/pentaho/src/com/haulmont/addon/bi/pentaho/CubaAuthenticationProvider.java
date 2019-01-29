/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.bi.pentaho;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class CubaAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authenticationRequest) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (GrantedAuthority originalAuth : authenticationRequest.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(originalAuth.getAuthority()));
	    }
        UsernamePasswordAuthenticationToken authenticationResult = new UsernamePasswordAuthenticationToken(authenticationRequest.getPrincipal(),
                authenticationRequest.getCredentials(), authorities);
        authenticationResult.setDetails(authenticationRequest.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class clazz) {
        return clazz.equals(CubaAuthenticationToken.class);
    }
}
