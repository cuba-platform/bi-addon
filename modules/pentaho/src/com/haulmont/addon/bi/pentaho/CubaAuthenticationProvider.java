/*
 * TODO Copyright
 */

/*
	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
