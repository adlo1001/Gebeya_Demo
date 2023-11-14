To add authentication and Authorization in your group code:
Check an application-- Gebeya_Demo on github.

1. Add all the dependencies in the pom.xml file 
2. Create a mode "UserModel" in the model package.
    check the code please.
3. Add AuthorizationFilter, AuthenticationFilter, CustomAuthenticationProvider, MKesy and SecurityConfig as shown in the code 
4. Add "/login" end point in the controller , and use @RequestBody <--- this is where you user passes his username and phon number. Just check if the phone number is registered in in your mobile table and allow access. 
check inside AuthenticationFilter
5. Once all is do, You try post for /login and when you do you generate a bearer token. Whenever you want to use other private endpoints you must use that token.

