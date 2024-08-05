insert into item_to_process (id, objet_id)
    select public.uuid_generate_v4(), id from credit
    WHERE statut_credit in ('VA' , 'SO', 'PX')
        and nbre_jours_retard > 365
        and nbre_jours_retard <= 730
        and ((ind_immobilise is null) or (ind_immobilise=false))
        and ind_type_souffrance != 3
ON CONFLICT (objet_id) DO NOTHING