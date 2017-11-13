/*  
 *   Copyright 2014 IT4biz IT Solutions Ltda
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 	 changed by it4biz.com.br
 */

var SaikuCubaBI = Backbone.View.extend({
        initialize: function(args) {
                this.workspace = args.workspace;
                if (Settings.GET.CUBA_VIEW_STATE != "undefined" && Settings.GET.CUBA_VIEW_STATE == "EDIT") {
                        $(this.workspace.toolbar.el).find('.open, .new, .switch_to_mdx, .mdx').hide()
                }
        }
});

 Saiku.events.bind('session:new', function(session) {
        function new_workspace(args) {
            if (typeof args.workspace.saikuCubaBI == "undefined") {             	   	
            	args.workspace.saikuCubaBI = new SaikuCubaBI({ workspace: args.workspace });
            }
        }
        Saiku.session.bind("workspace:new", new_workspace);
});
