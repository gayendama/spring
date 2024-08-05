insert into item_to_process (id, objet_id)
    select public.uuid_generate_v4(), id from Credit
    WHERE statut_credit in ('VA' , 'SO', 'PX')
        and nbre_jours_retard > 180
        and nbre_jours_retard <= 365
        and ((ind_immobilise is null) or (ind_immobilise=false))
        and ind_type_souffrance != 2
ON CONFLICT (objet_id) DO NOTHING